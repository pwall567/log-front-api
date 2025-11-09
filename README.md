# log-front-api

[![Build Status](https://github.com/pwall567/log-front-api/actions/workflows/build.yml/badge.svg)](https://github.com/pwall567/log-front-api/actions/workflows/build.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maven Central](https://img.shields.io/maven-central/v/io.jstuff/log-front-api?label=Maven%20Central)](https://central.sonatype.com/artifact/io.jstuff/log-front-api)

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

The original [`log-front`](https://github.com/pwall567/log-front) library has been modified to be an implementation of
this API library; the library [`log-front-kotlin`](https://github.com/pwall567/log-front-kotlin) is a Kotlin
implementation of the API.

## Quick Start

All logging is performed through an instance of the `Logger` interface, and such instances are obtained from an
implementation of the `LoggerFactory` interface.
A library class that requires to use logging should be provided with a `LoggerFactory`, either as a constructor
parameter or by dependency injection, and it can then use this to create `Logger` instances to perform the logging
operations.

```java
import io.jstuff.log.Logger;
import io.jstuff.log.LoggerFactory;

public class ClassThatLogs {
    private final Logger log;
    public ClassThatLogs(LoggerFactory<?> loggerFactory) {
        log = loggerFactory.getLogger();
    }
    public void doSomething(String value) {
        log.info(() -> "Doing something with " + value);
        // etc.
    }
}
```

## Logging Levels

The concept of a logging level appears in most logging libraries.
In `log-front-api` there are 5 levels, specified in the [`Level`](#level) enum.

It is possible (but not necessary) to specify the initial level for a `Logger` when requesting it from the
`LoggerFactory`; not all implementing classes make use of an initial level specified in this way.

## Time

It can be useful to be able to specify the time of a log entry, as opposed to letting the logging system apply the
current time (possibly using a supplied `Clock` &ndash; see [below](#clock)).
For example, when creating a database record, it can be valuable to have the log entry show the same creation time as
that stored in the record.
Also, when log entries are written in a separate thread from the one creating them, it is clearly preferable for the log
entries to be tagged with the time they were created, not when they are were later output.

To accommodate this requirement, all of the logging function have forms that take a time (as an `Instant`).
It should be noted that not all underlying log mechanisms allow the time to be specified, so in many cases this time
parameter will be ignored (in particular, `slf4j` lacks the ability to specify the time of a log entry).

## Clock

For testing purposes, a `java.time.Clock` object can be specified when requesting a `Logger`.
It can be useful in unit tests to provide a fixed `Clock` so that date- or time-sensitive operations can be tested
reliably.
Allowing the same `Clock` to be used for logging can assist in confirming that the log messages produced correspond with
the functions under test.

Few `Logger` implementations currently make use of a `Clock` specified in this manner, and for those that don&rsquo;t,
the functionality may safely be ignored.
If the `Clock` is not specified, the system clock will be used (unless the time is [specified explicitly](#time) on the
logging call).

## Reference

### LoggerFactory

#### Interface

`LoggerFactory` is a generic interface, parameterised by the type of `Logger` returned:
```java
public interface LoggerFactory<L extends Logger> {
    // contents...
}
```

#### `getLogger()`

The interface specifies a number of variations of the `getLogger()` function (the actual return type is `L`, the
parameterised type of the `LoggerFactory`):

- `Logger getLogger()`
- `Logger getLogger(Level level)`
- `Logger getLogger(Clock clock)`
- `Logger getLogger(Level level, Clock clock)`
- `Logger getLogger(Class<?> javaClass)`
- `Logger getLogger(Class<?> javaClass, Level level)`
- `Logger getLogger(Class<?> javaClass, Clock clock)`
- `Logger getLogger(Class<?> javaClass, Level level, Clock clock)`
- `Logger getLogger(String name)`
- `Logger getLogger(String name, Level level)`
- `Logger getLogger(String name, Clock clock)`
- `Logger getLogger(String name, Level level, Clock clock)`

Implementing classes need only implement the last function; default implementations for the other functions delegate to
the last function, supplying default values for the parameters as follows:

1. **Logger Name** &ndash; if a `Class<?>` is specified, the qualified class name of the class is used; if no logger
   name or class is provided, the qualified class name of the calling class is used.
2. **Level** &ndash; if no level is specified, the default level for the `LoggerFactory` is used (see
   [`getDefaultLevel()`](#getdefaultlevel)).
3. **Clock** &ndash; if no clock is specified, the default clock for the `LoggerFactory` is used (see
   [`getDefaultClock()`](#getdefaultclock)).

#### `getDefaultLevel()`

The `getDefaultLevel()` function returns the default level for this `LoggerFactory`:

- `Level getDefaultLevel()`

The default implementation of this function returns `Level.INFO`.

#### `setDefaultLevel()`

The `setDefaultLevel()` function sets the default level for this `LoggerFactory`:

- `void setDefaultLevel(Level level)`

The default implementation of this function stores the supplied value, to be returned by subsequent calls to
`getDefaultLevel()`

#### `getDefaultClock()`

The `getDefaultClock()` function returns the default clock for this `LoggerFactory`:

- `Clock getDefaultClock()`

The default implementation of this function returns the system clock.

#### `setDefaultClock()`

The `setDefaultClock()` function sets the default clock for this `LoggerFactory`:

- `void setDefaultClock(Clock clock)`

The default implementation of this function stores the supplied value, to be returned by subsequent calls to
`getDefaultClock()`

### Logger

The `Logger` interface specifies a number of logging operations related to the different logging levels:

- `void trace(Object message)`
- `void debug(Object message)`
- `void info(Object message)`
- `void warn(Object message)`
- `void error(Object message)`
- `void error(Throwable throwable, Object message)`
- `void log(Level level, Object message)`

The message is specified as being of type `Object`, and may be nullable &ndash; in most cases it will be converted to
`String` by `String.valueOf(message)` (or equivalent), but that is dependent on the `Logger` implementation.
The second `error` function takes a `Throwable` as well as the message; how the `Throwable` is handled also depends on
the implementation.
The last function in the list allows the level to be specified as a parameter.

To specify the time of a logging event explicitly, the followings forms are also available:

- `void trace(Instant time, Object message)`
- `void debug(Instant time, Object message)`
- `void info(Instant time, Object message)`
- `void warn(Instant time, Object message)`
- `void error(Instant time, Object message)`
- `void error(Instant time, Throwable throwable, Object message)`
- `void log(Instant time, Level level, Object message)`

As noted above, many implementations will ignore the time supplied.

There are also tests for the logging level of the `Logger`:

- `boolean isTraceEnabled()`
- `boolean isDebugEnabled()`
- `boolean isInfoEnabled()`
- `boolean isWarnEnabled()`
- `boolean isErrorEnabled()`
- `boolean isEnabled(Level level)`

The default implementations of these functions use the level in the `Logger`, not in the underlying implementation, so
unless the level is kept in sync with the implementation, the tests are not likely to be useful in that form, and must
be overridden.

Lastly, there are a set of logging functions that take a `Supplier<Object>` lambda parameter.
These all have default implementations that call the appropriate `isXxxxEnabled()` function, and only if true, invoke
lambda to create the message object.
This is the recommended means of logging complex messages, avoiding the cost of creating the message if it is not going
to be used.

- `void trace(Supplier<Object> messageSupplier)`
- `void debug(Supplier<Object> messageSupplier)`
- `void info(Supplier<Object> messageSupplier)`
- `void warn(Supplier<Object> messageSupplier)`
- `void error(Supplier<Object> messageSupplier)`
- `void error(Throwable throwable, Supplier<Object> messageSupplier)`
- `void log(Level level, Supplier<Object> messageSupplier)`

- `void trace(Instant time, Supplier<Object> messageSupplier)`
- `void debug(Instant time, Supplier<Object> messageSupplier)`
- `void info(Instant time, Supplier<Object> messageSupplier)`
- `void warn(Instant time, Supplier<Object> messageSupplier)`
- `void error(Instant time, Supplier<Object> messageSupplier)`
- `void error(Instant time, Throwable throwable, Supplier<Object> messageSupplier)`
- `void log(Instant time, Level level, Supplier<Object> messageSupplier)`

As with the other `error` functions, there are versions that take a `Throwable`.

### Level

The `Level` enum specifies five logging levels.
In increasing order of severity, they are:

- `TRACE`
- `DEBUG`
- `INFO`
- `WARN`
- `ERROR`

These will mostly correspond to the level mechanism of the underlying implementation, but the documentation for the
implementing class should be consulted for details.

The `Level` includes the function `isEnabled` to simplify testing whether the `Level` is enabled for a nominated level
and all higher levels:

- `boolean isEnabled(Level level)`

### LoggerException

All exceptions thrown by the library are instances of `LoggerException`, which extends `RuntimeException` (and are
therefore not checked exceptions).

### NullLoggerFactory

The `NullLoggerFactory` is an implementation of `LoggerFactory` that returns a `NullLogger`.

### NullLogger

A `NullLogger` is a `Logger` that outputs nothing &ndash; it includes null implementations of all the logging functions,
along with implementations of the `isEnabled()` functions that always return `false`.

## Dependency Specification

The latest version of the library is 3.0, and it may be obtained from the Maven Central repository.

### Maven
```xml
    <dependency>
      <groupId>io.jstuff</groupId>
      <artifactId>log-front-api</artifactId>
      <version>3.0</version>
    </dependency>
```
### Gradle
```groovy
    implementation 'io.jstuff:log-front-api:3.0'
```
### Gradle (kts)
```kotlin
    implementation("io.jstuff:log-front-api:3.0")
```

Peter Wall

2025-11-09
