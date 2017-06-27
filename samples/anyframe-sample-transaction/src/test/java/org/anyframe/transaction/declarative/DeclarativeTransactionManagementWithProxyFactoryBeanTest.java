/*
 * Copyright 2002-2009 the original author or authors.
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
package org.anyframe.transaction.declarative;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.anyframe.sample.domain.Genre;
import org.anyframe.sample.domain.Movie;
import org.anyframe.sample.transaction.moviefinder.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User Mgmt Service가 제공하는 기능을 테스트하기 위한 샘플 테스트 코드
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:/META-INF/spring/integration/common/context-annotation.xml",
        "classpath*:/META-INF/spring/integration/datasource/context-tx.xml",
        "classpath*:/META-INF/spring/integration/datasource/context-tx-datasource.xml",
        "classpath*:/META-INF/spring/integration/testMovie/context-user-proxyfactorybean.xml"
})
public class DeclarativeTransactionManagementWithProxyFactoryBeanTest {

	@Inject
	@Named("txMovieService")
	private MovieService movieService;

	/**
	 * This testcase is used to test the functionality of updateMovieList method
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	@Rollback(value = true)
	public void testUpdateMovieWithNotExistMovie() throws Exception {
		Movie newMovie = getMovie();
		String newMovieID = newMovie.getMovieId();
		Movie updateMovie = movieService.get("MV-00003");
		updateMovie.setMovieId("MV-11111");
		updateMovie.setTitle("TEST Movie");
		
		try {
			movieService.updateMovieList(newMovie, updateMovie);
			fail("fail to get user.");
		} catch (Exception e) {
			try {
				Movie movie =  movieService.get(newMovieID);
				assertNotNull("fail to commit.", movie);
			} catch (Exception ie) {
				fail("fail to trnasaction management.");
			}
		}
	}
	
	private Movie getMovie() throws Exception {
		Genre genre = new Genre();
		genre.setGenreId("GR-03");

		Movie movie = new Movie();
		movie.setMovieId("MV-" + System.currentTimeMillis());
		movie.setTitle("Shrek (2001)");
		movie.setActors("Shrek");
		movie.setDirector("Andrew Adamson");
		movie.setGenre(genre);
		movie.setReleaseDate(new Date());
		movie.setRuntime(new Long(90));
		movie.setTicketPrice(new Float(8000));
		movie.setNowPlaying("N");

		return movie;
	}
}
