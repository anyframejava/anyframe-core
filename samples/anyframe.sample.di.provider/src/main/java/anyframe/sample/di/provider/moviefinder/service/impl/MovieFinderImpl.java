package anyframe.sample.di.provider.moviefinder.service.impl;

import java.util.List;

import javax.inject.Named;

import anyframe.sample.di.provider.moviefinder.service.MovieFinder;
import anyframe.sample.domain.Movie;

@Named
public class MovieFinderImpl extends GenericMovieFinder<MovieDao> implements
		MovieFinder {
	public List<Movie> getPagingList(Movie movie, int pageIndex)
			throws Exception {
		// get movieDao instance calling get()
		MovieDao movieDao = get();
		System.out.println("#1 MovieDao instance : " + movieDao);

		movieDao = get();
		System.out.println("#2 MovieDao instance : " + movieDao);

		return movieDao.getPagingList(movie, pageIndex);
	}
}
