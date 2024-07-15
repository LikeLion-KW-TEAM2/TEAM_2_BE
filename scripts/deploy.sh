REPOSITORY=/home/ubuntu/app
JAR_NAME=$(ls -tr $REPOSITORY/*SNAPSHOT.jar | tail -n 1)

echo "> 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fla java | grep $JAR_NAME | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"
echo "> JAR NAME: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

# nohup.out을 REPOSITORY 디렉토리에 저장하도록 경로 변경
nohup java -jar -Duser.timezone=Asia/Seoul $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

# STDOUT 닫기
exec 1>&-
