CREATE TABLE accident_rule (
  id serial primary key,
  accident_id int references accident(id),
  rule_id int references rule(id)
);