#!/bin/bash

filepath=$(cd "$(dirname "$0")"; pwd)
opt_array=(
    "--network=enp2s0"
    "--spring.pidfile=$filepath/.pid"
    "--logging.path=$filepath/logs"
    "--box.serial-number=celloud-1"
)
jarfile=$filepath/box-1.0.1-SNAPSHOT.jar
function start(){
	check
	num=$?
	if [ $num -eq 0 ]
	then
		echo 'Application is already running !'
	else
        opts=""
        for opt in ${opt_array[@]}
        do
            opts="$opts $opt"
        done
        nohup java -jar $jarfile $opts > $filepath/nohup.out 2>&1 &
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
