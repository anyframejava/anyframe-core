/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.anyframe.sample.datasource;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author SoYon Lim
 * @author JongHoon Kim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./src/test/resources/spring/context-*.xml" })
public abstract class AbstractDatasourceServiceTest{
	protected DataSource dataSource = null;

	protected boolean isSuccessful;

	protected int connectionCount;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Test
	public void testGetConnection() throws Exception {

		LinkedList connectionList = new LinkedList();

		// get a connection
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con
				.prepareStatement("select 'aaa' from dual");
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			System.out.println(rs.getString(1));
		}
		rs.close();
		pstmt.close();
		con.close();
	}

	@Test
	public void testNormalUse() throws Exception {
		this.isSuccessful = true;
		Thread[] thread = new Thread[120];

		try {

			this.connectionCount = 0;

			for (int i = 0; i < 3; i++) {
				thread[i] = (new Thread(new ConnectionThread(this, dataSource)));
				thread[i].start();
			}

			for (int i = 0; i < 3; i++)
				thread[i].join();

			System.out.println("The normal use test passed with "
					+ this.connectionCount
					+ " requests and 10 concurrent threads running");
		} finally {
			assertTrue("The DataSourceComponent could not be retrieved.",
					null != dataSource);
		}

		assertTrue("Normal use test failed", this.isSuccessful);
	}

	class ConnectionThread implements Runnable {
		protected DataSource datasource;

		protected AbstractDatasourceServiceTest testcase;

		ConnectionThread(AbstractDatasourceServiceTest testcase,
				final DataSource datasource) {
			this.datasource = datasource;
			this.testcase = testcase;
		}

		public void run() {
			long end = System.currentTimeMillis() + 7000; // run for 7
			// seconds
			Random rnd = new Random();

			while (System.currentTimeMillis() < end
					&& this.testcase.isSuccessful) {
				try {
					Connection con = this.datasource.getConnection();
					Thread.sleep((long) rnd.nextInt(100));
					con.close();
					this.testcase.connectionCount++;
				} catch (final SQLException se) {
					System.out.println("Failed to get Connection, test failed");
				} catch (final Exception ie) {
				}
			}
		}
	}
}
