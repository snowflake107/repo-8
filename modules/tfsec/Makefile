INSTALL_PATH ?= $(BUILD_HARNESS_PATH)/vendor
TFSEC ?= $(INSTALL_PATH)/tfsec
TFSEC_VERSION ?= v1.28.1

## Install tfsec
tfsec/install: packages/install/tfsec
	@exit 0

## Static code security analysis
tfsec/run:
	$(TFSEC) .
