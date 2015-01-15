<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<div class="row">
    <#if model.hasUser()>
        <#--<div class="page-header">-->
            <#--<h2>Create new post</h2>-->
        <#--</div>-->
        <div class="col-md-12">
            <form class="form" role="form" method="POST" action="new" >
                <div class="page-header">
                    <div class="form-group">
                        <input type="text" class="form-control" id="title" name="title"
                               placeholder="Post Title">
                    </div>
                </div>
                <div class="form-group">
                    <textarea id="body" class="form-control final" rows="10" name="body"></textarea>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-primary pull-right" role="button" onclick="location.href='/'">Cancel</button>
                        <button type="submit" class="btn btn-primar pull-right" name="saveButton" id="save-post-button">Save</button>
                        <button type="button" class="btn btn-danger" id="recordButton">
                            Record
                        </button>
                        <button type="button" class="btn btn-danger" id="stopButton">
                            Stop
                        </button>
                        <p><span class="interim" id="content_curr"></span></p>

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

//            for get location (in future)
//            if (navigator.geolocation) {
//                navigator.geolocation.getCurrentPosition(showPosition);
//            }
//            else{
//                console.log('no geo')
//            }
//            function showPosition(position) {
//                console.log(position.coords.latitude);
//                console.log(position.coords.longitude);
//            }

            var format = webspeechkit.FORMAT.PCM16;

            $('#body').html('');
            $('#content_curr').html('');

            dict.start(format,
                    function(){},
                    function(msg) { console.log(msg); alert(msg); },
                    function(text, uttr, merge) {
                        if (uttr) {

                            var val = $('#new-post-body-textarea').val();
                            $('#new-post-body-textarea').val(val + ' ' + text);

                        }
                        $('#content_curr').html(text);
                    },
                    function(info) {
                        $('#save-post-button').css('backgroundColor','hsla(' + info.maxFreq + ',60%,60%,0.3)');
                        $('#save-post-button').val(info.maxFreq);
                    }
            );
        };

        stopButton.onclick = dict.stop;

    </script>

</div>
</@layout.layout>
