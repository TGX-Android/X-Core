import tgx.gradle.flavorImplementation

plugins {
  id("java-toolchain-convention")
  id(libs.plugins.android.library.get().pluginId)
  id("tgx-module")
}

dependencies {
  flavorImplementation(
    libs.androidx.core.ktx.legacy,
    libs.androidx.core.ktx.lollipop,
    libs.androidx.core.ktx.latest
  )
}

android {
  namespace = "me.vkryl.core"
}