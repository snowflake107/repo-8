package hudson.plugins.ec2.win;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.ec2.model.*;
import hudson.model.Descriptor;
import hudson.model.TaskListener;
import hudson.plugins.ec2.*;
import hudson.slaves.*;
import jenkins.slaves.JnlpAgentReceiver;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EC2WindowsJNLPLauncher extends DelegatingComputerLauncher {
    final long sleepBetweenAttempts = TimeUnit.SECONDS.toMillis(10);
    private final static Logger LOGGER = Logger.getLogger(EC2WindowsJNLPLauncher.class.getName());


    public EC2WindowsJNLPLauncher(WindowsJNLPData amiType) {
        super(new JNLPLauncher(amiType.getTunnel()));

    }

    @Override
    public Descriptor<ComputerLauncher> getDescriptor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void launch(SlaveComputer computer, TaskListener listener) {

        try {

            final PrintStream logger = listener.getLogger();
            EC2AbstractSlave node = (EC2AbstractSlave) computer.getNode();
            EC2Computer ec2Computer = (EC2Computer)computer;
            logger.println("wtf ec2Computer.getName(): " + ec2Computer.getName());
            logger.println("hmmmmmmm: " + ec2Computer.getJnlpMac());

            Instance instance = ec2Computer.updateInstanceDescription();
            try {
                CreateTagsRequest ctr = new CreateTagsRequest(List.of(instance.getInstanceId()),
                        List.of(
                                new Tag("agentName", node.getNodeName()),
                                new Tag("jnlpMac", ec2Computer.getJnlpMac())));
                ec2Computer.getCloud().connect().createTags(ctr);
                logger.println(String.format("Tagged instance [%s]", instance.getInstanceId()));
            } catch (AmazonClientException e) {
                LOGGER.log(Level.SEVERE, String.format("Failed to tag instance [%s]", instance.getInstanceId()), e);
            }

            logger.println(node.getDisplayName() + " booted at " + node.getCreatedTime());

            final long minTimeout = 3000;
            long timeout = node.getLaunchTimeoutInMillis(); // timeout is less than 0 when jenkins is booting up.
            if (timeout < minTimeout) {
                timeout = minTimeout;
            }
            final long startTime = System.currentTimeMillis();
            final SlaveTemplate template = ec2Computer.getSlaveTemplate();
            while (true) {
                node = (EC2AbstractSlave) computer.getNode();
                long waitTime = System.currentTimeMillis() - startTime;
                if (waitTime > timeout) {
                    throw new AmazonClientException("Timed out after " + (waitTime / 1000)
                            + " seconds of waiting for agent to be connected");
                }

                if (!node.isConnected) {
                    logger.println("Waiting for agent to connect... sleeping 10s");
                    Thread.sleep(sleepBetweenAttempts);
                    continue;
                }

                logger.println("Agent has connected with the controller!");
                return; // successfully connected
            }
        } catch (AmazonClientException e) {
            e.printStackTrace(listener.error(e.getMessage()));
            if (computer.getNode() instanceof  EC2AbstractSlave) {
                LOGGER.log(Level.FINE, String.format("Terminating the ec2 agent %s due a problem launching or connecting to it", computer.getName()), e);
                EC2AbstractSlave ec2AbstractSlave = (EC2AbstractSlave) computer.getNode();
                if (ec2AbstractSlave != null) {
                    ec2AbstractSlave.terminate();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace(listener.error(e.getMessage()));
            if (computer.getNode() instanceof  EC2AbstractSlave) {
                LOGGER.log(Level.FINE, String.format("Terminating the ec2 agent %s due a problem launching or connecting to it", computer.getName()), e);
                EC2AbstractSlave ec2AbstractSlave = (EC2AbstractSlave) computer.getNode();
                if (ec2AbstractSlave != null) {
                    ec2AbstractSlave.terminate();
                }
            }
        }
    }
}
