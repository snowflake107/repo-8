INSTALL_PATH ?= $(BUILD_HARNESS_PATH)/vendor
TFLINT ?= $(INSTALL_PATH)/tflint
TFLINT_VERSION ?= 0.46.1

## Install tflint
tflint/install: packages/install/tflint
	@exit 0

## A Pluggable Terraform Linter
tflint/run:
	$(TFLINT) --init
	$(TFLINT) -f compact
