package ajman.university.grad.project.eventshare.common.services;

import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.contracts.ITagService;

public class ServicesFactory {
	private static ILocalStorageService _localStorageService;
	private static ITagService _tagService;
	
	public static ILocalStorageService getLocalStorageService() {
		if (_localStorageService == null) {
			_localStorageService = new LocalStorageService();
		}
		
		return _localStorageService;
	}

	public static ITagService getTagService() {
		if (_tagService == null) {
			_tagService = new TagService();
		}
		
		return _tagService;
	}
}
