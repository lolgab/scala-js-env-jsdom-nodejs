# scalajs-env-jsdom-nodejs

This is a fork of [scalajs-env-jsdom-nodejs](https://github.com/scala-js/scala-js-env-jsdom-nodejs)
providing a JavaScript environment for Scala.js (a `JSEnv`) running [Node.js](https://nodejs.org/) with
[jsdom](https://github.com/jsdom/jsdom),
and with `require` function which allows to require/import Node.js modules (e.g. `fs`).

You may find `require` function useful when you need to read/write files during testing, for example.

When working with Node.js modules, [scala-js-nodejs](https://github.com/exoego/scala-js-nodejs)
might be handy since it provide types and utility functions.

## Usage

**Ensure `jsdom@16.3.0` or above is installed.**

Add the following line to `project/plugins.sbt`:

```scala
libraryDependencies += "net.exoego" %% "scalajs-env-jsdom-nodejs" % "2.1.0"
```

and the following line to `build.sbt` (possibly in the `settings`/`jsSettings` of Scala.js projects):

```scala
jsEnv := new net.exoego.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
```
