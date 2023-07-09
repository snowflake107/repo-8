package hudson.plugins.ec2;

import com.google.common.base.Objects;
import hudson.Extension;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

public class WindowsJNLPData extends AMITypeData {

    private final String bootDelay;
    private final String tunnel;

    @DataBoundConstructor
    public WindowsJNLPData(String bootDelay, String tunnel) {

        this.bootDelay = bootDelay;
        this.tunnel = tunnel;
    }

    @Extension
    public static class DescriptorImpl extends Descriptor<AMITypeData> {
        @Override
        public String getDisplayName() {
            return "windowsJNLP";
        }
    }

    @Override
    public boolean isWindows() {
        return true;
    }

    @Override
    public boolean isUnix() {
        return false;
    }

    @Override
    public boolean isMac() {
        return false;
    }

    @Override
    public String getBootDelay() {
        return this.bootDelay;
    }

    public String getTunnel() {
        return tunnel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WindowsJNLPData that = (WindowsJNLPData) o;
        return Objects.equal(bootDelay, that.bootDelay);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bootDelay);
    }
}
