Protogram Webapp
================

Live at https://mattprecious.github.io/protogram/

Currently the website deploys automatically from the `master` branch.


Development
-----------

Run `./gradlew :webapp:run --continuous` and a local webserver and browser will be opened.
Code changes will be automatically picked up and compiled. The browser will also refresh
automatically.


Release
-------

Running `./gradlew :webapp:installDist` will create an unpacked version of the final site
at `webapp/build/install/webapp/`. For a zip of the site, run `./gradlew :webapp:distZip` instead
which will put a zip in `webapp/build/distributions/`.
