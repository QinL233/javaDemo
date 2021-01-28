package jdbc.template;

import jdbc.util.JdbcUtils;

import java.sql.*;

public class JdbcTemplate {

	private  Object execute(String sql, RowMapper<?> rowMapper) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Object result = null;
		try {
			if (conn == null) {
				conn = JdbcUtils.getConnection();
			}
			if (conn == null) {
				throw new IllegalArgumentException("get connection error...");
			}
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (stmt != null) {
				rs = stmt.executeQuery(sql);
				result = rowMapper.mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private Object execute(String sql, RowMapper<?> rowMapper, Object... objects) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object result = null;
		try {
			if (conn == null) {
				conn = JdbcUtils.getConnection();
			}
			if (conn == null) {
				throw new IllegalArgumentException("get connection error...");
			}
			pstmt = conn.prepareStatement(sql);
			if (pstmt != null) {
				int i = 1;
				for (Object object : objects) {
					if (object instanceof String) {
						pstmt.setString(i++, (String) object);
					} else if (object instanceof Integer) {
						pstmt.setInt(i++, (Integer) object);
					} else if (object instanceof Double) {
						pstmt.setDouble(i++, (Double) object);
					} else if (object instanceof Long) {
						pstmt.setLong(i++, (Long) object);
					} else if (object instanceof Boolean) {
						pstmt.setBoolean(i++, (Boolean) object);
					} else if (object == null) {
						pstmt.setTimestamp(i++, null);
					} else {
						throw new IllegalArgumentException("sql param error...");
					}
				}
				rs = pstmt.executeQuery();
				result = rowMapper.mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

	private int executeUpdate(String sql) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		try {
			if (conn == null) {
				conn = JdbcUtils.getConnection();
			}
			if (conn == null) {
				throw new IllegalArgumentException("get connection error...");
			}

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (stmt != null) {
				result = stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private int executeUpdate(String sql, Object... objects) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			if (conn == null) {
				conn = JdbcUtils.getConnection();
			}
			if (conn == null) {
				throw new IllegalArgumentException("get connection error...");
			}
			pstmt = conn.prepareStatement(sql);
			if (pstmt != null) {
				int i = 1;
				for (Object object : objects) {
					if (object instanceof String) {
						pstmt.setString(i++, (String) object);
					} else if (object instanceof Integer) {
						pstmt.setInt(i++, (Integer) object);
					} else if (object instanceof Double) {
						pstmt.setDouble(i++, (Double) object);
					} else if (object instanceof Long) {
						pstmt.setLong(i++, (Long) object);
					} else if (object instanceof Boolean) {
						pstmt.setBoolean(i++, (Boolean) object);
					} else {
						throw new IllegalArgumentException("sql param error...");
					}
				}
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private int executeUpdate(String sql, PreparedStatementSetter preparedStatementSetter) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			if (conn == null) {
				conn = JdbcUtils.getConnection();
			}
			if (conn == null) {
				throw new IllegalArgumentException("get connection error...");
			}
			pstmt = conn.prepareStatement(sql);
			pstmt = preparedStatementSetter.setValues(pstmt);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@SuppressWarnings("resource")
	private int[] executeBatch(BatchStatementSetter batchStatementSetter) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		int[] result = null;
		try {
			if (conn == null) {
				conn = JdbcUtils.getConnection();
			}
			if (conn == null) {
				throw new IllegalArgumentException("get connection error...");
			}

			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			if (stmt != null) {
				stmt = batchStatementSetter.setValues(stmt);
				result = stmt.executeBatch();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			if (conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@SuppressWarnings("resource")
	private int[] executeBatch(String sql, BatchPreparedStatementSetter batchPreparedStatementSetter)
			throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int[] result = null;
		try {
			if (conn == null) {
				conn = JdbcUtils.getConnection();
			}
			if (conn == null) {
				throw new IllegalArgumentException("get connection error...");

			}
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			if (pstmt != null) {
				pstmt = batchPreparedStatementSetter.setValues(pstmt);
				pstmt.executeBatch();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			if (conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private boolean executeCall(String sql) throws SQLException {
		Connection conn = null;
		CallableStatement cstmt = null;
		boolean result = false;
		try {
			if (conn == null) {
				conn = JdbcUtils.getConnection();
			}
			if (conn == null) {
				throw new IllegalArgumentException("get connection error...");
			}
			cstmt = conn.prepareCall(sql);
			result = cstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	private Object executeCall(String sql, CallableStatementSetter callableStatementSetter) throws SQLException {
		Connection conn = null;
		CallableStatement cstmt = null;
		boolean result = false;
		try {
			if (conn == null) {
				conn = JdbcUtils.getConnection();
			}
			if (conn == null) {
				throw new IllegalArgumentException("get connection error...");
			}
			cstmt = conn.prepareCall(sql);
			cstmt = callableStatementSetter.setValues(cstmt);
			// result = cstmt.execute();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	public Object query(String sql, RowMapper<?> rowMapper) throws SQLException {
		return execute(sql, rowMapper);
	}

	public Object query(String sql, RowMapper<?> rowMapper, Object... objects) throws SQLException {
		return execute(sql, rowMapper, objects);
	}

	public int update(String sql) throws SQLException {
		return executeUpdate(sql);
	}

	public int update(String sql, Object... objects) throws SQLException {
		return executeUpdate(sql, objects);
	}

	public int update(String sql, PreparedStatementSetter preparedStatementSetter) throws SQLException {
		return executeUpdate(sql, preparedStatementSetter);
	}

	public int[] batchUpdate(BatchStatementSetter batchStatementSetter) throws SQLException {
		return executeBatch(batchStatementSetter);
	}

	public int[] batchUpdate(String sql, BatchPreparedStatementSetter batchPreparedStatementSetter)
			throws SQLException {
		return executeBatch(sql, batchPreparedStatementSetter);
	}

	public boolean call(String sql) throws SQLException {
		return executeCall(sql);
	}

	public Object call(String sql, CallableStatementSetter callableStatementSetter) throws SQLException {
		return executeCall(sql, callableStatementSetter);
	}
}
