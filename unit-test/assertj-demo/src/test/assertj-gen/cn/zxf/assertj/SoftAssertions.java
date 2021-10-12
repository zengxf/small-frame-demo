package cn.zxf.assertj;

/**
 * Entry point for soft assertions of different data types.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class SoftAssertions extends org.assertj.core.api.SoftAssertions {

  /**
   * Creates a new "soft" instance of <code>{@link cn.zxf.assertj.TestUserAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public cn.zxf.assertj.TestUserAssert assertThat(cn.zxf.assertj.TestUser actual) {
    return proxy(cn.zxf.assertj.TestUserAssert.class, cn.zxf.assertj.TestUser.class, actual);
  }

  /**
   * Creates a new "soft" instance of <code>{@link cn.zxf.assertj.UserAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created "soft" assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public cn.zxf.assertj.UserAssert assertThat(cn.zxf.assertj.User actual) {
    return proxy(cn.zxf.assertj.UserAssert.class, cn.zxf.assertj.User.class, actual);
  }

}
