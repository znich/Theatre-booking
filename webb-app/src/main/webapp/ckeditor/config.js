/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	 config.language = 'ru';
	 config.skin = 'moonocolor';
	// config.uiColor = '#AADC6E';
	 config.toolbarGroups = [
	                          { name: 'document',    groups: [ 'mode', 'document', 'doctools' ] },
	                          { name: 'tools' },
	                          { name: 'clipboard',  },
	                          { name: 'links' },
	                          { name: 'insert' },	                         
	                          { name: 'others' },
	                          { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
	                          { name: 'paragraph',   groups: [ 'list', 'indent', 'blocks', 'align' ] },
	                          { name: 'styles' },
	                          { name: 'colors' },
	                          { name: 'about' }
	                      ];
};
