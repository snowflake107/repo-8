FLAKE8 ?= $(BUILD_HARNESS_PATH)/vendor/flake8
FLAKE8_VERSION ?= 4.0.1
FLAKE8_TARGET_PATH ?= $(BUILD_HARNESS_PATH)/vendor/.flake8

## Install flake8
flake8/install:
	@[ -x $(FLAKE8) ] || ( \
		echo "* Installing flake8 $(FLAKE8_VERSION) ($(OS)) from pip" && \
		python3 -m venv $(CHECKOV_TARGET_PATH) && \
		source $(CHECKOV_TARGET_PATH)/bin/activate && \
		pip install --no-cache-dir --disable-pip-version-check flake8==$(FLAKE8_VERSION) && \
		ln -f -s $(FLAKE8_TARGET_PATH)/bin/flake8 $(FLAKE8) \
	)
	$(FLAKE8) --version
