import com.typesafe.sbt.site.util.SiteHelpers
import Docs._

lazy val tutorialSubDirName = settingKey[String]("subdir name for old tutorial")
lazy val fileEncoding = settingKey[String]("check the file encoding")

ThisBuild / organization := "org.scala-sbt"
ThisBuild / scalafmtOnCompile := true

lazy val root = (project in file("."))
  .enablePlugins(
    (if (!isBetaBranch) Seq(NanocPlugin) else Seq()) ++
      Seq(LowTechSnippetPamfletPlugin, ScriptedPlugin): _*
  )
  .settings(
    name := "website",
    siteEmail := "eed3si9n" + "@gmail.com",
    // Reference
    sourceDirectory in Pamflet := baseDirectory.value / "src" / "reference",
    siteSubdirName in Pamflet := s"""$targetSbtBinaryVersion/docs""",
    tutorialSubDirName := s"""$targetSbtBinaryVersion/tutorial""",
    // Redirects
    redirectSettings,
    SiteHelpers.addMappingsToSiteDir(mappings in Redirect, siteSubdirName in Pamflet),
    redirectTutorialSettings,
    SiteHelpers.addMappingsToSiteDir(mappings in RedirectTutorial, tutorialSubDirName),
    // GitHub Pages. See project/Docs.scala
    customGhPagesSettings,
    if (scala.sys.BooleanProp.keyExists("sbt.website.generate_pdf"))
      Def settings (
        // NOTE - PDF settings must be done externally like this because pdf generation generically looks
        // through `mappings in Config` for Combined+Pages.md to generate PDF from, and therefore we
        // can't create a circular dependency by adding it back into the original mappings.
        Pdf.settings,
        Pdf.settingsFor(Pamflet, "sbt-reference"),
        SiteHelpers.addMappingsToSiteDir(
          mappings in Pdf.generatePdf in Pamflet,
          siteSubdirName in Pamflet,
        )
      )
    else Nil,
    fileEncoding := {
      sys.props("file.encoding") match {
        case "UTF-8" => "UTF-8"
        case x       => sys.error(s"Unexpected encoding $x")
      }
    },
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    isGenerateSiteMap := true
  )
