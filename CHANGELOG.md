# Change log

All notable changes to the LaunchDarkly SDK for Haskell - Redis Integration will be documented in this file. This project adheres to [Semantic Versioning](https://semver.org).



## [1.1.0](https://github.com/snowflake107/repo-8/compare/v1.0.0...1.1.0) (2024-10-16)


### Features

* Initial port of functionality from SDK repository ([#1](https://github.com/snowflake107/repo-8/issues/1)) ([07235d6](https://github.com/snowflake107/repo-8/commit/07235d613dc22ca78a96d9169db85fb98d570f25))


### Bug Fixes

* Update default redis prefix to correct casing ([#4](https://github.com/snowflake107/repo-8/issues/4)) ([e74973a](https://github.com/snowflake107/repo-8/commit/e74973a1e5d0d02d9a3d17fb5761fd724de1c9b3))

## [1.0.0] - 2023-02-21
### Added:
- Moved the previous redis integration package out of the [Haskell SDK repository](https://github.com/launchdarkly/haskell-server-sdk) into this standalone package.

### Changed:
- Updated default namespace from "LaunchDarkly" to "launchdarkly" to match other redis integration defaults.
- Updated packages to minimums allowed by SDK.
