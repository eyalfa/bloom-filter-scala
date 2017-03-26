import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import pl.project13.scala.sbt.JmhPlugin
import sbt._

object TheBuild extends Build {

  lazy val root = Project("bloom-filter-root", file("."))
      .aggregate(bloomFilter, bloomFilterKryo, tests, examples)
      .configs(Configs.all: _*)
      .settings(Settings.root: _*)

  lazy val bloomFilter = Project("bloom-filter", file("bloom-filter"))
      .configs(Configs.all: _*)
      .settings(Settings.bloomfilter: _*)

  lazy val bloomFilterKryo = Project("bloom-filter-kryo", file("bloom-filter-kryo"))
    .configs(Configs.all: _*)
    .settings(Settings.bloomfilterKryo: _*)
    .dependsOn(bloomFilter)

  lazy val sandbox = Project("sandbox", file("sandbox"))
      .dependsOn(bloomFilter)
      .configs(Configs.all: _*)
      .settings(Settings.sandbox: _*)

  lazy val sandboxApp = Project("sandboxApp", file("sandboxApp"))
      .dependsOn(bloomFilter)
      .configs(Configs.all: _*)
      .settings(Settings.sandboxApp: _*)
      .enablePlugins(JavaAppPackaging)

  lazy val tests = Project("tests", file("tests"))
      .dependsOn(bloomFilter, bloomFilterKryo, sandbox)
      .configs(Configs.all: _*)
      .settings(Settings.tests: _*)

  lazy val benchmarks = Project("benchmarks", file("benchmarks"))
      .dependsOn(bloomFilter, sandbox)
      .configs(Configs.all: _*)
      .settings(Settings.benchmarks: _*)
      .enablePlugins(JmhPlugin)

  lazy val examples = Project("examples", file("examples"))
      .dependsOn(bloomFilter)
      .configs(Configs.all: _*)
      .settings(Settings.examples: _*)
      .enablePlugins(JavaAppPackaging)

}

