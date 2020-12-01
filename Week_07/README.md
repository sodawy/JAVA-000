第7周作业

# 作业-插入1M数据 (存储过程-单事务-实行1min55s)
```sql
delimiter ;;
create procedure generate_order2()
begin
    declare i int;
    set i = 1;
    while (i < 1000000)
        do
            insert into t_order
            values (i, #order_id
                    CAST(rand() * 12 as SIGNED) + 1, #user_id  
                    CAST(rand() * 13 as SIGNED) + 1, #merchandise_id
                    NOW(), #create_time
                    NOW(), #update_time
                    'available' #status
                   );
            set i = i + 1;
        end while;
end;
call generate_order2;
```