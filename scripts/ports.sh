#!/bin/bash

argument=$1

if [ "$argument" == "start" ]; then
	postgres_pod=$(kubectl get pods | grep postgres)
	postgres_pod_name=$(echo $postgres_pod | awk '{print $1;}')
	postgres_pod_status=$(echo $postgres_pod | awk '{print $3;}')

  if [ "$postgres_pod_status" != "Running" ]; then
    echo "Postgres pod status is ${postgres_pod_status}, cannot port-forward"
  else
    echo "Postgres pod is  running, I'm gonna port forward for you :)"
    kubectl port-forward $postgres_pod_name 5431:5432 &
  fi
else
	if [ "$argument" == "stop" ]; then
		for pid in $(ps -ef | grep "kubectl" | awk '{print $2}'); do
			kill -9 $pid;
			echo "Killed process with id $pid"
		done
	else
		echo "Argument invalid, can be only start|stop"
	fi
fi