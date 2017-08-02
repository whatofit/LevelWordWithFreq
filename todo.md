5、条件查询QueryBuilder的使用
上述代码其实已经用到了简单的条件查询了：
1、简单的where等于
articleDaoOpe.queryBuilder().where().eq("user_id", userId).query();直接返回Article的列表

2、where and 

	QueryBuilder<Article, Integer> queryBuilder = articleDaoOpe
					.queryBuilder();
			Where<Article, Integer> where = queryBuilder.where();
			where.eq("user_id", 1);
			where.and();
			where.eq("name", "xxx");

			//或者
			articleDaoOpe.queryBuilder().//
					where().//
					eq("user_id", 1).and().//
					eq("name", "xxx");
					
					
上述两种都相当与：select * from tb_article where user_id = 1 and name = 'xxx' ; 

3、更复杂的查询

where.or(
					//
					where.and(//
							where.eq("user_id", 1), where.eq("name", "xxx")),
					where.and(//
							where.eq("user_id", 2), where.eq("name", "yyy")));
							
							
select * from tb_article where ( user_id = 1 and name = 'xxx' )  or ( user_id = 2 and name = 'yyy' )  ;
好了，再复杂的查询估计也能够凑出来了~~