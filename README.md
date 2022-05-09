# log-front-api

[![Build Status](https://travis-ci.com/pwall567/log-front-api.svg?branch=main)](https://app.travis-ci.com/github/pwall567/log-front-api)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maven Central](https://img.shields.io/maven-central/v/net.pwall.log/log-front-api?label=Maven%20Central)](https://search.maven.org/search?q=g:%22net.pwall.log%22%20AND%20a:%22log-front-api%22)

Logging Interface API

## Background

The library is a further development (and simplification) of the library
[`log-front`](https://github.com/pwall567/log-front).
That library was an attempt to solve a long-standing problem in the creation of utility libraries:
- the library wishes to make use of logging
- the library wishes to have its own logging integrated with the logging output by the overall project
- the library will be only a part of any project that makes use of it, and cannot dictate the form of logging used in
  the project as a whole
- the library must not bring in transitive dependencies that may cause conflicts, or that may cause conditional loading
  to make incorrect assumptions about the form of logging in use
- the library must be simple to integrate, and must not require `<exclusions>` (Maven) or `exclude` (Gradle) to avoid
  incorrect dependency usage

This library provides a minimum set of interfaces which do not include the underlying functionality, allowing utility
libraries to be created with no references to implementation classes.

## Quick Start

All logging is performed through an instance of the `Logger` interface, and such instances are obtained from an
implementation of the `LoggerInterface` interface.
A library class that requires to use logging should be provided with a `LoggerFactory`, either as a constructor
parameter or by dependency injection, and it can then use this to create `Logger` instances to perform the logging
operations.

```java
import net.pwall.log.Logger;
import net.pwall.log.LoggerFactory;

public class ClassThatLogs {
    private final Logger log;
    public ClassThatLogs(LoggerFactory<?> loggerFactory) {
        log = loggerFactory.getLogger(); // do we want to get a Logger for every instance? (might be best option)
    }
    public void doSomething(String value) {
        log.info(() -> "Doing something with " + value);
        // etc.
    }
}
```

## Logging Levels

The concept of a logging level appears in most logging libraries.
In `log-front-api` there are 5 levels, specified in the `Level` `enum`.
They are (in increasing severity):

- `TRACE`
- `DEBUG`
- `INFO`
- `WARN`
- `ERROR`

It is possible (but not necessary) to specify the initial level for a `Logger` when requesting it from the
`LoggerFactory`; not all implementing classes make use of an initial level specified in this way.

## Clock

For testing purposes, a `Clock` object can be specified when requesting a `Logger`.
It can be useful in unit tests to provide a fixed `Clock` so that date- or time-sensitive operations can be tested
reliably.
Allowing the same `Clock` to be used for logging can assist in confirming that the log messages produced correspond with
the functions under test.

Few `Logeer` implementations currently make use of a `Clock` specified in this manner, and for those that don&rsquo;t,
the functionality may safely be ignored.
If the `Clock` is not specified, the system clock will be used.

## Reference

### LoggerFactory

The `LoggerFactory` interface is parameterised by the type of `Logger` returned.
It specifies a number of variations of the `getLogger()` function, but only one is required to be implemented; the
others all have default implementations which delegate to the main function.

- `Logger getLogger(String name, Level level, Clock clock)`

The other variations omit one or more of the parameters, and the defaults are as follows:

- `name`: the fully-qualified class name of the class from which the function is called
- `level`: `INFO`
- `clock`: the system clock

There are also versions that take a Java `Class`; the fully qualified class name will be used as the name.

### Logger

The `Logger` interface specifies a number of logging operations related to the different logging levels:

- `void trace(Object message)`
- `void debug(Object message)`
- `void info(Object message)`
- `void warn(Object message)`
- `void error(Object message)`
- `void error(Throwable throwable, Object message)`

The message is specified as being of type `Object`, and may be nullable &ndash; in most cases it will be converted to
`String` by `String.valueOf(message)` (or equivalent), but that is dependent on the implementation.
The last function takes a `Throwable` as well as the message; how the `Throwable` is handled also depends on the
implementation.

There are also tests for the logging level of the `Logger`:

- `boolean isTraceEnabled()`
- `boolean isDebugEnabled()`
- `boolean isInfoEnabled()`
- `boolean isWarnEnabled()`
- `boolean isErrorEnabled()`

The default implementations of these functions use the level in the `Logger`, not in the underlying implementation, so
unless the level is kept in sync with the implementation, the tests are not likely to be useful in that form, and must
be overridden.

Lastly, there are a set logging functions that take a `Supplier<Object>` lambda parameter.
These all have default implementations that call the appropriate `isXxxxEnabled()` function, and only if true, invoke
lambda to create the message object.
This is recommended means of logging complex messages, but avoiding the cost of creating the message if it is not going
to be used.

- `void trace(Supplier<Object> messageSupplier)`
- `void debug(Supplier<Object> messageSupplier)`
- `void info(Supplier<Object> messageSupplier)`
- `void warn(Supplier<Object> messageSupplier)`
- `void error(Supplier<Object> messageSupplier)`
- `void error(Throwable throwable, Supplier<Object> messageSupplier)`

As with the other `error` function, there is a version that takes a `Throwable`.

### NullLoggerFactory

The `NullLoggerFactory` is an implementation of `LoggerFactory` that returns a `NullLogger`.

### NullLogger

A `NullLogger` is a `Logger` that outputs nothing &ndash; it includes null implementations of all the logging functions.

## Dependency Specification

The latest version of the library is 1.1, and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>net.pwall.log</groupId>
      <artifactId>log-front-api</artifactId>
      <version>1.1</version>
    </dependency>
```
### Gradle
```groovy
    implementation 'net.pwall.log:log-front-api:1.1'
```
### Gradle (kts)
```kotlin
    implementation("net.pwall.log:log-front-api:1.1")
```

Peter Wall

2022-05-10
