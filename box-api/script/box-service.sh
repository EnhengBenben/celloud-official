#!/bin/bash

filepath=$(cd "$(dirname "$0")"; pwd)
opts_network="--network=enp2s0"
opts_pidfile="--spring.pidfile=$filepath/.pid"
opts_logpath="--logging.path=$filepath/logs"
opts_serial="--box.serial-number=celloud-1"
opts="$opts_logpath $opts_network $opts_pidfile $opts_serial"
function start(){
	check
	num=$?
	if [ $num -eq 0 ]
	then
		echo 'Application is already running !'
	else
		nohup java -jar $filepath/box-1.0.1-SNAPSHOT.jar $opts > $filepath/nohup.out 2>&1 & 
		echo 'Application is started !'
	fi
}

function stop(){
	check
	num=$?
	if [ $num -eq 0 ]
	then
		tpid=`cat $filepath/.pid | awk '{print $1}'`
		tpid=`ps -aef | grep $tpid | awk '{print $2}' |grep $tpid`
		if [ ${tpid} ]
		then
			kill $tpid
			echo 'Application is stopped !'
		fi
	else
		echo 'Application is already stopped !'
	fi
}

function status(){
	check
	num=$?
	if [ $num -eq 0 ]
	then
		echo 'Application is running !'
	elif [ $num -eq 1 ]
	then
		echo 'Pid file is not exists , application is not running !'
	elif [ $num -eq 2 ]
	then
		echo 'Pid is invalid , application is not running !'
	fi
	return $num

}
function restart(){
	stop
	sleep 3s
	start
}
# 0=running,1=no pid file,2=invalid pid
function check(){
	if [ ! -f $filepath/.pid ]
	then
	    return 1
	else
		tpid=`cat $filepath/.pid | awk '{print $1}'`
		tpid=`ps -aef | grep $tpid | awk '{print $2}' |grep $tpid`
		
		if [ ${tpid} ]
		then
			return 0
		else
			return 2
		fi
	fi
}

case "$1" in 
	start)
		start
		;;
	stop)
		stop
		;;
	restart)
		restart
		;;
	status)
		status
		;;
	*)
		echo "Usage: $0 {start|stop|restart|status}"
		exit 1
		;;
esac
exit 0
