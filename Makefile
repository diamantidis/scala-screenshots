.PHONY: help
.DEFAULT_GOAL := help

CONFIG_SCOPE:="universal"

PROG:="scala-screenshots"
VERSION:=1.0
PREFIX:="${PWD}/target/${CONFIG_SCOPE}"
PROG_BASE_DIR:="${PREFIX}/${PROG}-${VERSION}"
ZIP_DIR:="${PROG_BASE_DIR}.zip"

PROG_DIR:="${PROG_BASE_DIR}/bin/${PROG}"

SYMLINK_DIR:="/usr/local/bin"

install: ## Builds and install
	@echo "Creating package...."
	@sbt "$(CONFIG_SCOPE):packageBin"
	@echo "Done packaging"
	@echo "Unzipping is $(ZIP_DIR)"

	@ unzip -o $(ZIP_DIR) -d ${PREFIX}

	@echo "Done unzipping"

	@echo "Creating symlink from ${PROG_DIR} to $(SYMLINK_DIR)"

	@ln -sf ${PROG_DIR} $(SYMLINK_DIR)

	@echo "Done. You can now run the command 'scala-screenshots'"

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
