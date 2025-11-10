# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep all model classes
-keep class com.example.buyandsell.models.** { *; }

# Keep all utility classes
-keep class com.example.buyandsell.utils.** { *; }

# Keep all activity classes
-keep class com.example.buyandsell.activity.** { *; }

# Keep all fragment classes
-keep class com.example.buyandsell.Fragment.** { *; }

# Keep all adapter classes
-keep class com.example.buyandsell.adapters.** { *; }

# Keep anonymous inner classes
-keepclassmembers class * {
    public <init>(...);
}

# Keep all public methods in activities
-keep public class * extends android.app.Activity {
    public void *(android.view.View);
}

# Keep all public methods in fragments
-keep public class * extends androidx.fragment.app.Fragment {
    public void *(android.view.View);
}

# Keep all public methods in adapters
-keep public class * extends androidx.recyclerview.widget.RecyclerView$Adapter {
    public void *(android.view.View);
}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile