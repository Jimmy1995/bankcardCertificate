/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
    //config.uiColor = '#AADC6E';
	if(typeof ctx !='undefined'){
		config.filebrowserBrowseUrl = ctx+'/cms/browse.jsp';  
	    config.filebrowserImageBrowseUrl = ctx+'/cms/browse.jsp?type=Images';  
	    config.filebrowserFlashBrowseUrl = ctx+'/cms/browse.jsp?type=Flashs';  
	    config.filebrowserUploadUrl = ctx+'/cms/article-manage!upload.action';
	    config.filebrowserImageUploadUrl = ctx+'/cms/article-manage!upload.action?type=Images';  
	    config.filebrowserFlashUploadUrl = ctx+'/cms/article-manage!upload.action?type=Flashs';  
	}
    /**config.filebrowserWindowWidth = '640';  
    config.filebrowserWindowHeight = '480';**/  
    //config.contentsCss = CKEDITOR.getUrl( 'mycss.css' );
	config.toolbarGroups = [
	                		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
	                		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
	                		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
	                		{ name: 'colors', groups: [ 'colors' ] },
	                		
	                		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
	                		{ name: 'forms', groups: [ 'forms' ] },
	                		
	                		
	                		
	                		{ name: 'tools', groups: [ 'tools' ] },
	                		
	                		{ name: 'styles', groups: [ 'styles' ] },
	                		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
	                		{ name: 'links', groups: [ 'links' ] },
	                		{ name: 'insert', groups: [ 'insert' ] },
	                		{ name: 'others', groups: [ 'others' ] }
	                	];

	                	config.removeButtons = 'About,Language,Flash,Print,Save,Templates,NewPage,Cut,Copy';
};
