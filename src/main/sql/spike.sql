/*
 存储过程
 */
 DELIMITER $$
 /**
   定义存储过程
   in 表示输入参数，out表示输出参数
   row_count()函数表示返回上一条修改类型的sql(delete,update,insert)的影响行数
   row_count():0表示没有修改数据，>0表示修改的行数，<0sql错误
  */

  create procedure execute_spike
(in v_spike_id bigint,in v_phone bigint,
in v_kill_time timestamp,out r_result int)
begin
    declare insert_count int default 0;
    start transaction;
    insert into success_spike_t
        (spike_id, user_phone, create_time)
        values (v_spike_id,v_phone,v_kill_time);
    select row_count() into insert_count;
    if (insert_count=0) then
        rollback;
        set r_result=-1;
    elseif (insert_count<0) then
        rollback;
        set r_result=-2;
    else
        update spike_t
        set number=number-1
        where spike_id=v_spike_id
            and start_time<v_kill_time
            and end_time>v_kill_time
            and number>0;
        select row_count() into insert_count;
        if (insert_count=0) then
            rollback;
            set r_result=0;
        elseif (insert_count<0) then
            rollback;
            set r_result=-2;
        else
            commit;
            set r_result=1;
        end if;
    end if;
end $$

DELIMITER ;
set @r_result=-3;
-- 执行存储过程
call execute_spike(1003,13422424244,now(),@r_result);
