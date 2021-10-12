package cn.zxf.assertj;

/**
 * Entry point for BDD assertions of different data types.
 */
@javax.annotation.Generated(value="assertj-assertions-generator")
public class BddAssertions {

  /**
   * Creates a new instance of <code>{@link cn.zxf.assertj.TestUserAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static cn.zxf.assertj.TestUserAssert then(cn.zxf.assertj.TestUser actual) {
    return new cn.zxf.assertj.TestUserAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link cn.zxf.assertj.UserAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  @org.assertj.core.util.CheckReturnValue
  public static cn.zxf.assertj.UserAssert then(cn.zxf.assertj.User actual) {
    return new cn.zxf.assertj.UserAssert(actual);
  }

  /**
   * Creates a new <code>{@link BddAssertions}</code>.
   */
  protected BddAssertions() {
    // empty
  }
}
