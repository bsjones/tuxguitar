#!/bin/bash
jdb -classpath $(echo $INSTALLPATH/usr/share/java/tuxguitar/*.jar | tr ' ' ':'):/usr/share/java/apache-commons-cli.jar org.herac.tuxguitar.app.tools.custom.converter.TGConverterMain $@
