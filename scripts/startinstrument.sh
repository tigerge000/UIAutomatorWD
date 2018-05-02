#!/system/bin/sh
am instrument -w -r -e port $1 -e class 'com.macaca.android.testing.UIAutomatorWD' com.macaca.android.testing.test/android.support.test.runner.AndroidJUnitRunner