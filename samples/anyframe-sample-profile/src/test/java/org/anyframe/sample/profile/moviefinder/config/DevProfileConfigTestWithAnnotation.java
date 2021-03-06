/*
 * Copyright 2008-2012 the original author or authors.
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
package org.anyframe.sample.profile.moviefinder.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.anyframe.sample.profile.moviefinder.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * This class is an annotation based Test Case class for "dev" profile defined
 * configuration class.
 * 
 * @author Heewon Jung
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DefaultMovieConfig.class,
		DevMovieConfig.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
public class DevProfileConfigTestWithAnnotation {

	@Autowired
	private Movie movie;

	@Test
	public void getMovie() {
		assertNotNull(movie);
		assertEquals("Shrek", movie.getTitle());
	}

}