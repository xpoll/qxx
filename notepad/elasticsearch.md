# elasticsearch

curl -x[method] [http://ip:port/_index/_type/_id]?pretty
pretty 美化输出

curl -XGET http://127.0.0.1:9200
curl -XPUT
curl -XPOST http://127.0.0.1:9200/lm/test/1 -d '{"name":"lili","age":30,"about":"i like ping pang song","like":["ping pong","song"]}'
curl -XPOST http://127.0.0.1:9200/lm/test/2 -d '{"name":"lihua","age":25,"about":"i like ping pang song","like":["ping pong","read"]}'
curl -XPOST http://127.0.0.1:9200/lm/test/3 -d '{"name":"tom","age":25,"about":"i like ping pang read","like":["ping pong"]}'
curl -XPOST http://127.0.0.1:9200/lm/test/4 -d '{"name":"tom","age":30,"about":"i like ping read","like":["ping pong"]}'
curl -XPOST http://127.0.0.1:9200/lm/test -d '{"name":"tok","age":30,"about":"i like ai","like":["sing"]}'


curl -XGET http://127.0.0.1:9200/lm/test/1

curl -XGET http://127.0.0.1:9200/lm/test/_search
curl -XGET http://127.0.0.1:9200/lm/test/_search?q=name:lili
curl -XGET http://127.0.0.1:9200/lm/test/_search -d '{"query":{"match":{"name":"tom"}}}'
curl -XGET http://127.0.0.1:9200/lm/test/_search -d '{"query":{"filtered":{"filter":{"range":{"age":{"gt":25}}},"query":{"match":{"name":"tom"}}}}}'
curl -XGET http://127.0.0.1:9200/lm/test/_search -d '{"query":{"match":{"about":"read"}},"highlight":{"fields":{"about":{}}}}'

curl -XGET http://127.0.0.1:9200/lm/test/_search -d '{"aggs":{"all_like":{"terms":{"field":"like"}}}}'

curl -XGET http://127.0.0.1:9200/lm/test/_search?_source=name
curl -XGET http://127.0.0.1:9200/lm/test/1/_source

curl -i -XHEAD http://127.0.0.1:9200/lm/test/1

curl -XPOST http://127.0.0.1:9200/lm/test/1/_create -d '{"name":"lili","age":30,"about":"i like ping pang song","like":["ping pong","song"]}'		--# _create

curl -XDELETE http://127.0.0.1:9200/lm/test/AVsYMiKUVEhI-dMU3KQo


index/_analyze?analyzer=ik&pretty=true&text="中国有13亿人口"
