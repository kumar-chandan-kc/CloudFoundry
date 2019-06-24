# CloudFoundry
CloundFoundry test code
virtualization vs containerization

-no support for xs on hcp because unable to update java version
to v8 spidermonkey(doesnt support browserless javascript) was not efficient

- what is hypervisor ?
- why not hcp ?
- what is oauth?
ans like we used in xtreamit, its a token used in the request header to
reach the server. the admin can deactivate the token anytime


- cloudfoundry provides all paas code on github and anyone can
modify it to make it optimal for their purpose. ex: blumix for watson purposes.


- service broker is the second last layer in cloudfoudnry and it exposes backend
services like hana db, cassandra db, rabbit mq.


- what is droplet in cloudfoundry?
  Every droplet has runtime, which is taken from the yml that is nothing but
  a kind of builder script like pom.xml.

- elk- elasti search, log stash, kibana

- diego replaced dea in cf(sap).

- npm install, npm install express

- express 'nameofproject'

- set http_proxy=http://proxy.wdf.sap.corp:8080
-cf login -a https://api.cf.sap.hana.ondemand.com

-npm install swagger
- swagger project create helloswagger.
->then select express(adds express dependencies)

- swagger project edit.
- swagger start project -m

- go to .project of existing projects and copy paste it.
- node-debug app.js

installation of cloudfoundry, mongodb and other stuff:
CF Cli: https://github.com/cloudfoundry/cli#downloads
NodeJS: https://nodejs.org/en/download/
EnIDE: http://www.nodeclipse.org/enide/2015/
mongodb: https://www.mkyong.com/mongodb/how-to-install-mongodb-on-windows/

npm install mongo --save or npm install --save mongo
npm install --save mongodb

- run mongod first and then mongo

npm install node-inspector

cf push or cf push -nostart

service bindings to create a new instance of mongodb

difference between hcp and cloudfoundry ?
hcp is not partition tolerant, they increase cpu size always and hana is not scalable, it can hold max 128tb.
also application level, it doesnt supprot other runtimes. different subscription for hcp.


QWhat is rabbit mq?
ans in the past, smart bus was used to communicate between services but the problem is it is not scalable.
So for microsoervices, dumb pipes started being used which have no code but just a messaging channel
that creates a ticket and sends it ot suer, hence facilitating asynchronous service communication.
Rabbit mq is a dumb pipe.

Q what is chaos monkey?
ans Chaos monkey puts pressure on the microservice in isolation and can bring out the flaws in service.

Q what is hystrix ?
Ans Hystrix is a library that helps handle threads that connect to various services and thereby giving us some control over request resposnse circuits.


TEST paradigm
sertup, given, when, then, cleanup

Things to keep in mind:
1) keep calls asynchronous as much as possible.
2) keep fallback calls.
3) use design patterns(specially singleton and prototype coupled with factory)
4) tests written only for business logic(unit tests).
5) use message services for asynchronous calls.
6) use jmeter to do performance tests on service endpoints.
7) contract tests ? new but enforce on cloudfoundry.


data.d.results[ver].count;

git add .
git commit -m "commit message"
git remote add origin <your url/sAMe one used for cloning>
git push origin master


use this to avoid certificate problems while pushing from git
git config --global http.sslVerify false


git command to stage all changes
git add $(git rev-parse --show-toplevel)

git command to stage a particular entity
git stage <filename>
Q how to check logs in cloudfoundry ?
ANS 'cf logs HCPBEH --recent' (HCPBEH is appname as in manifest.yml)

properties for using ODATA(OnPremise) on hcp

WebIDEUsage   abap_odata,odata_gen
WebIDEEnabled true


creating instance from command prompt using cf client

cf create-service xsuaa application trial-uaa -c “{\”xsappname\”:\”scope-check\”}”
.


opening an ssh channel for any service/database

cf ssh -L localhost:8089:10.11.241.96:49703 postgresI331677 

where localhost:8089 is internal host from where things are to be channeled from

ipaddress is of the db

postgres I331677 is the name of the applciation to which the database is inded to 




multi target application usage:
create mta.yaml like in test data management
download cf plugin from here: https://github.com/cloudfoundry-incubator/multiapps-cli-plugin
and install using cf install-plugin [location of downloaded .exe]

download mta builder from here.https://tools.hana.ondemand.com/#cloudh

right click project and go to properties - > go to builder - > 'new' builder.

in the first input give ${system_path:java}
in the second input, browse for your parent project.

in the arguments give 

-jar "C:\Users\I331677\Downloads\mta_archive_builder-1.1.7.jar" --build-target CF --mtar ./target/test-data-manager.mtar build

the link to the downloaded jar file should be given over there.


to deploy, use cf deploy target\[mtarlocation]




java remote debugging for CF:

- cf ssh <app name> -c "app/META-INF/.sap_java_buildpack/sapjvm/bin/jvmmon"
helps connect to jvm on cf for that particular app

- type 'start debugging'

- open another command prompt and type cf ssh <app name> -N -T -L 8000:127.0.0.1:8000

- while the above step is running, go to eclipse and go to debug configurations of that project and in the drop down, select socket attach and start debugging.

- run your project on cf and keep debugging points in eclipse, it should stop on coming to the debug points.

If this doesn't work, do the following


cf set-env <app name> JAVA_OPTS '-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000'

restart the app on cf

and run the command
cf ssh <app name> -N -T -L 8000:127.0.0.1:8000

and then just go to eclipse and do as mentioned in the previous steps

HOW TO RUN MONGO DB LOCALLY

open mongo db folder in command prompt.
execute command mongod.exe --dbpath "C:\mongodb\data" 



git checkout 'commit hash' filepath

