# New version publication

This page describes how to publish a new version of plugin and all related artifacts.

Steps:

1. Create a PR with version increment in [gradle.properties](../gradle.properties). Please follow [semver guidelines](https://semver.org/):
   1. If there are breaking `.api` files - increment the major version (e.g. from `x.y.z` to `x+1.0.0`)
   2. If current version is compatible with previous one and there are some notable changes - please increment the minor one (e.g. from `x.y.z` to `x.y+1.0`)
   3. Otherwise - please increment the patch version (e.g. from `x.y.z` to `x.y.z+1`).
2. Receive API Key and Secret from the portal (please
   follow [the documentation](https://docs.gradle.org/current/userguide/publishing_gradle_plugins.html)) and add them into the project secrets.
3. Run publication workflows:
   1. Navigate to [Actions](https://github.com/Citi/gradle-helm-plugin/actions/workflows/) and locate
   the [publish workflow](./workflows/publish.yaml) and [publish documentation one](./workflows/publish.yaml). GitHub
   documentation: https://docs.github.com/en/actions/using-workflows/manually-running-a-workflow
   2. Run both workflows
4. Release [the version on GitHub](https://docs.github.com/en/repositories/releasing-projects-on-github).