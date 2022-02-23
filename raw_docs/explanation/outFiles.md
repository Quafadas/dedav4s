# Where do the temporary files live?

By default, the OS temp directory. 

You may specify the location of temporary files through configuration. Either by having a suitably located "application.conf", or by passing in the environment variable ```DEDAV_OUT_PATH```.

e.g. 
```
sbt -DDEDAV_OUT_PATH=/Users/simon/Pictures   
```