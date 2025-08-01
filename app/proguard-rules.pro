# General Android settings
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preserve annotations (for libraries like Gson, Retrofit, Room, etc.)
-keepattributes *Annotation*
-keepattributes Signature, InnerClasses

-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean

# Keep BouncyCastle classes
-keep class org.bouncycastle.jsse.** { *; }

# Keep Conscrypt classes
-keep class org.conscrypt.** { *; }

# Keep OpenJSSE classes
-keep class org.openjsse.** { *; }

-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean

# Suppress warnings for missing BouncyCastle classes
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider

# Suppress warnings for missing Conscrypt classes
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier

# Suppress warnings for missing OpenJSSE classes
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE

-dontwarn coil3.PlatformContext

-keep public class com.vk.push.** extends android.os.Parcelable

# Keep application model classes (serialization/deserialization)
-keep class samaryanin.avitofork.** { *; }

# For Gson (JSON serialization/deserialization)
-keep class com.google.gson.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes AnnotationDefault, RuntimeVisibleAnnotations

# Keep classes extending TypeToken (for generic handling in Gson)
-keep class * extends com.google.gson.reflect.TypeToken

# For Jetpack Compose (UI Framework)
-keep class androidx.compose.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.navigation.** { *; }

# For Coroutines
-dontwarn kotlinx.coroutines.**
-keep class kotlinx.coroutines.** { *; }

# Optional: Hide source file names to improve obfuscation
# -renamesourcefileattribute ObfuscatedFile

# Prevent stripping of enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Prevent stripping of Parcelable implementations
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# Optional: For debugging stack traces
# Uncomment the following to keep method names intact (useful for debugging but reduces obfuscation)
-keep class samaryanin.avitofork.** { *; }
-keepattributes SourceFile,LineNumberTable
-keep class * {
    public *;
}

# Optional: Exclude logging libraries like Timber from obfuscation
-keep class timber.log.** { *; }
-dontwarn timber.log.**

# Exclude generated code from being stripped (like Dagger, Hilt, etc.)
-keep class dagger.** { *; }
-dontwarn dagger.**
-keep class javax.inject.** { *; }
-dontwarn javax.inject.**

# Optional: For Kotlin-specific classes
-keep class kotlin.** { *; }
-keepclassmembers class kotlin.** { *; }
-dontwarn kotlin.**

-dontwarn com.google.api.client.http.GenericUrl
-dontwarn com.google.api.client.http.HttpHeaders
-dontwarn com.google.api.client.http.HttpRequest
-dontwarn com.google.api.client.http.HttpRequestFactory
-dontwarn com.google.api.client.http.HttpResponse
-dontwarn com.google.api.client.http.HttpTransport
-dontwarn com.google.api.client.http.javanet.NetHttpTransport$Builder
-dontwarn com.google.api.client.http.javanet.NetHttpTransport
-dontwarn com.google.errorprone.annotations.CanIgnoreReturnValue
-dontwarn com.google.errorprone.annotations.CheckReturnValue
-dontwarn com.google.errorprone.annotations.Immutable
-dontwarn com.google.errorprone.annotations.InlineMe
-dontwarn com.google.errorprone.annotations.RestrictedApi
-dontwarn org.joda.time.Instant

-dontwarn android.test.**
