all: clean judge_serve judge_client
judge_serve:judge_serve.cpp logger.o configer.o
	g++ judge_serve.cpp logger.o configer.o -o judge_serve -lmysqlclient -L/usr/lib64/mysql -std=c++11
judge_client:judge_client.cpp client.o logger.o configer.o cdbc.o
	g++ judge_client.cpp client.o logger.o configer.o cdbc.o -o judge_client -lmysqlclient -L/usr/lib64/mysql -std=c++11
client.o:client.cpp
	g++ -c client.cpp
cdbc.o:cdbc.cpp logger.o configer.o
	g++ -c cdbc.cpp
configer.o:configer.cpp logger.o
	g++ -c configer.cpp
logger.o:logger.cpp
	g++ -c logger.cpp
clean:
	rm -rf judge_serve judge_client *.o