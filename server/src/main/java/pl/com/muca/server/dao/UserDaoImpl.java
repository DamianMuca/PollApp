package pl.com.muca.server.dao;

import com.google.common.collect.ImmutableList;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.com.muca.server.entity.User;
import pl.com.muca.server.mapper.UserRowMapper;

@Repository
public class UserDaoImpl implements UserDao {

  private static final String UPDATE_SQL =
      "UPDATE appuser SET name=:name, last_name=:last_name, "
          + "password_hash=:password_hash, email=:email "
          + "WHERE user_id_hash=:user_id_hash";

  private final NamedParameterJdbcTemplate template;

  public UserDaoImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public ImmutableList<User> findAll() {
    return ImmutableList
        .copyOf(template.query("SELECT * FROM appuser", new UserRowMapper()));
  }

  @Override
  public void insertUser(User user) {
    final String sql = "INSERT INTO appuser("
        + "user_id_hash, name, last_name, password_hash, email) "
        + "VALUES (:user_id_hash, :name, :last_name, :password_hash, :email)";

    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        // TODO (Damian Muca): 5/28/20 handle hash generating.
        .addValue("user_id_hash", user.getEmail().trim().hashCode())
        .addValue("name", user.getName().trim())
        .addValue("last_name", user.getLastName().trim())
        .addValue("password_hash", user.getPasswordHash())
        .addValue("email", user.getEmail().trim());
    template.update(sql, param, holder);
  }

  @Override
  public void updateUser(User user) {
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("user_id_hash", user.getUserIdHash())
        .addValue("name", user.getName().trim())
        .addValue("last_name", user.getLastName().trim())
        .addValue("password_hash", user.getPasswordHash())
        .addValue("email", user.getEmail().trim());
    template.update(UPDATE_SQL, param, holder);
  }

  @Override
  public void executeUpdateUser(User user) {
    Map<String, Object> map = new HashMap<>();
    map.put("user_id_hash", user.getUserIdHash());
    map.put("name", user.getName().trim());
    map.put("last_name", user.getLastName().trim());
    map.put("password_hash", user.getPasswordHash());
    map.put("email", user.getEmail().trim());

    template.execute(UPDATE_SQL, map,
        (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
  }

  @Override
  public void deleteUser(User user) {
    final String sql = "DELETE FROM appuser WHERE user_id_hash=:user_id_hash";
    final Map<String, Object> map = new HashMap<>();
    map.put("user_id_hash", user.getUserIdHash());
    template.execute(sql, map,
        (PreparedStatementCallback<Object>) PreparedStatement::executeUpdate);
  }
}
