## 一.使用Nacos实现动态路由

### 1.1 示例JSON配置

```json
{
	"filters": [],
	"id": "jd_route",
	"order": 0,
	"predicates": [{
		"args": {
			"pattern": "/jd"
		},
		"name": "Path"
	}],
	"uri": "http://www.jd.com"
}
```
