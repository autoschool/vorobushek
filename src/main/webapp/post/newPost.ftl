<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<div class="row">
    <#if model.hasUser()>
        <div class="page-header">
            <h2>Create new post</h2>
        </div>
        <div class="col-md-12">
            <form class="form" role="form" method="POST" action="new" >
                <div class="page-header">
                    <div class="form-group">
                        <input type="text" class="form-control" id="title" name="title"
                               placeholder="Post Title">
                    </div>
                </div>
                <div class="form-group">
                    <textarea id="new-post-body-textarea" class="form-control final" rows="10" name="body"></textarea>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <button id="save-post-button" type="submit" class="btn btn-primar pull-right">Save</button>
                        <#--<button type="button" id="recordButton" data-loading-text="Recording..." class="btn btn-primary" autocomplete="off">-->
                            <#--Record-->
                        <#--</button>-->
                        <button type="button" class="btn btn-danger" id="recordButton">
                            Record
                        </button>
                        <button type="button" class="btn btn-danger" id="stopButton">
                            Stop
                        </button>

                        <#--<h2>Recordings</h2>-->
                        <#--<ul id="recordingslist"></ul>-->


                        <#--<div class="btn-group" data-toggle="buttons">-->
                            <#--<label class="btn btn-primary active">-->
                                <#--<input type="radio" name="rec" id="stopRec" autocomplete="off" checked> Stop-->
                            <#--</label>-->
                            <#--<label class="btn btn-primary ">-->
                                <#--<input type="radio" name="rec" id="startRec" autocomplete="off" > Record-->
                            <#--</label>-->
                        <#--</div>-->
                        <span class="interim" id="content_curr"></span>
                    </div>
                </div>
            </form>
        </div>
    <#else>
        <div class="page-header">
            <h2>Guest can't add any posts. Please, login and try again.</h2>
        </div>
    </#if>

    <script>
        var uuid = '${model.getYandexSpeechKitKey()}'.replace(/[xy]/g, function(c) {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });

        var key = '${model.getYandexSpeechKitKey()}'

        var dict = new webspeechkit.Dictation("wss://webasr.yandex.net/asrsocket.ws", uuid, key);


        recordButton.onclick = function() {
            var format = webspeechkit.FORMAT.PCM16;

            $('#new-post-body-textarea').html('');
            $('#content_curr').html('');
            dict.start(format,
                    function(){},
                    function(msg) {console.log(msg); alert(msg);},
                    function(text, uttr, merge) {
                        if (uttr) {

                            var val = $('#new-post-body-textarea').val();
                            $('#new-post-body-textarea').val(val + ' ' + text);

                        }
                        $('#content_curr').html(text);
                        //console.log(text);
                    },
                    function(info) {}
            );
        };

//        function createDownloadLink() {
//
//            dict.recorder && dict.recorder.exportWAV(function(blob) {
//                var url = URL.createObjectURL(blob);
//                var li = document.createElement('li');
//                var au = document.createElement('audio');
//                var hf = document.createElement('a');
//
//                au.controls = true;
//                au.src = url;
//                hf.href = url;
//                hf.download = new Date().toISOString() + '.wav';
//                hf.innerHTML = hf.download;
//                li.appendChild(au);
//                li.appendChild(hf);
//                recordingslist.appendChild(li);
//            });
//        }

        stopButton.onclick = dict.stop;
//            createDownloadLink();

    </script>

</div>
</@layout.layout>
