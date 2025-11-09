# Change Log

The format is based on [Keep a Changelog](http://keepachangelog.com/).

## [3.0] - 2025-11-09
### Changed
- `Level`: added `isEnabled()` function
- `Logger`: switched to use `Level.isEnabled()`
- `Logger`: added functions that take a time parameter

## [2.1] - 2025-02-23
### Added
- `LoggerException`: exception class for exceptions thrown within the library
### Changed
- `LoggerFactory`: added function to check logger name
- `NullLoggerFactory`: added check of logger name
- `README.md`: major improvements

## [2.0] - 2025-01-29
### Added
- `build.yml`, `deploy.yml`: converted project to GitHub Actions
### Changed
- `pom.xml`: updated plugin versions
- `pom.xml`: moved to `io.jstuff` (package amd Maven group)
### Removed
- `.travis.yml`

## [1.3] - 2022-10-19
### Changed
- `Logger`, `NullLogger`: added `isEnabled` for use with dynamic log level

## [1.2] - 2022-05-22
### Changed
- `LoggerFactory`: minor optimisations
- `NullLogger`: added overrides for isEnabled functions

## [1.1] - 2022-05-10
### Changed
- `LoggerFactory`: added versions of `getLogger()` that take Java `Class`

## [1.0.1] - 2022-05-09
### Changed
- `pom.xml`: removed dependencies inadvertently included

## [1.0] - 2022-05-09
### Added
- all classes: initial versions
