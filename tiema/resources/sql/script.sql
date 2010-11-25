create or replace view view_user as
 select tu.*,(
      select wmsys.wm_concat(r.roleName)
  from t_role r, t_user u, t_user_role_relation trr
 where r.id = trr.role_id
   and u.id = trr.user_id
   and u.id = tu.id
 group by 1
 ) as roleNames from t_user tu