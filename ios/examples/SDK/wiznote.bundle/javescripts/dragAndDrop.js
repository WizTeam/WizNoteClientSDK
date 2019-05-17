
function drop(e) {
    var files = e.dataTransfer.files;
    if (files.length === 0) {
        return;
    }
    //
//    console.log(files);
    //
    var count = files.length;
    e.preventDefault();
    WizEditor.range.moveToPoint(e.clientX, e.clientY);
    //
    for (var i=0; i<count; i++) {
        //
        var f = e.dataTransfer.files[i];
//        console.log(f);
        var fileType = f.type;
        var fileName = f.name;
        var fileReader = new FileReader();
        fileReader.onload = didload(fileName, fileType, i, count);
        fileReader.readAsDataURL(f);
    }
}

function didload(name, type, index, totalCount){
    return function(e){
        // e.target是一个filereader，其中result是图片的base64信息
        // console.log(e.target.result);    //base64编码的图片信息
        var base64Data = e.target.result;
        //
        var message = {'data': base64Data, 'name': name, 'type': type, 'index': index, 'totalCount': totalCount};
        window.webkit.messageHandlers.didGetFileData.postMessage(message);
    }
}
//
document.body.ondrop=drop;




