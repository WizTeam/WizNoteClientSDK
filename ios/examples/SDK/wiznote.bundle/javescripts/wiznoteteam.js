
var WizNoteIOS = {

    isIphone : function () {

    },

    setResultCode : function (code) {
        window.location.href = "wiznoteteam://setResultCode" + "?code=" + code;
    },

    openURLInDefaultBrowser : function (url) {
        window.location.href = "wiznoteteam://openURLInDefaultBrowser" + "?url=" + url;
    }

};