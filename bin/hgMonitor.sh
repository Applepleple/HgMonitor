#!/bin/sh

if [ "$JAVA_HOME" != "" ]; then
  JAVA="$JAVA_HOME/bin/java"
else
  JAVA=java
fi

if readlink -f "$0" > /dev/null 2>&1
then
  HGBIN=`readlink -f "$0"`
else
  HGBIN="$0"
fi
HGBINDIR=`dirname "$HGBIN"`

if [ -z $HGCFGDIR ]
then
  HGCFGDIR="$HGBINDIR/../conf"
fi

if [ -z $HGCFG ]
then
    HGCFG="conf.ini"
fi

HGCFG="$HGCFGDIR/$HGCFG"

if [ -z $HGPIDFILE ]
then 
  HGPIDFILE="$HGBINDIR/../logs/hg_deamon.pid"
fi

if [ -z $HGLOGFILE ]
then 
  HGLOGFILE="$HGBINDIR/../logs/hgmonitor.out"
fi

if [ -z $HGJARLIB ]
then
  HGJARLIB=`ls "$HGBINDIR"/../target/*.jar`
fi

if [ ! -d "$HGBINDIR/../logs" ]
then
  cd $HGBINDIR/..
  mkdir logs
fi

#for i in "$HGBINDIR"/../lib/*.jar
#do
#    CLASSPATH="$i:$CLASSPATH"
#done

#if [ -z $HGMAIN ]
#then 
#  HGMAIN="edu.fudan.hgmonitor.HgMonitorMain"
#fi

for i in "$HGBINDIR"/../lib/native/*.so
do
    HGNATIVELIB="`readlink -f $i`:$HGNATIVELIB"
done

case $1 in
start)
    echo  -n "Starting hgmonitor ... "
    if [ -f $HGPIDFILE ]; then
      if kill -0 `cat $HGPIDFILE` > /dev/null 2>&1; then
         echo $command already running as process `cat $HGPIDFILE`. 
         exit 0
      fi
    fi
    nohup $JAVA -jar "$HGJARLIB" $JVMFLAGS \
    -c $HGCFG -n $HGNATIVELIB > "$HGLOGFILE" 2>&1 < /dev/null &
    if [ $? -eq 0 ]
    then
      if /bin/echo -n $! > "$HGPIDFILE"
      then
        sleep 1
        echo STARTED
      else
        echo FAILED TO WRITE PID
        exit 1
      fi
    else
      echo SERVER DID NOT START
      exit 1
    fi
	;;
stop)
    echo -n "Stopping hgmonitor ... "
    if [ ! -f "$HGPIDFILE" ]
    then
      echo "no hgmonitor to stop (could not find file $ZOOPIDFILE)"
    else
      kill -9 $(cat "$HGPIDFILE")
      rm "$HGPIDFILE"
      echo STOPPED
    fi
    ;;
*)
    echo "Usage: $0 {start|stop|}" >&2

esac
