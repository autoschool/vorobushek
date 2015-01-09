<#-- @ftlvariable name="model" type="ru.qatools.school.vorobushek.models.UserContext" -->
<#import "../layouts/main.ftl" as layout />
<@layout.layout title="Blog: posts" userName=model.getCurrentUserString() yandexLoginUrl=model.getUserUrl()>
<div class="row">

        <div class="col-md-12">
            <form class="form" role="form" method="POST" action="edit">
                <div class="page-header">
                    <div class="form-group">
                        <input type="text" id="edit-title-input" class="form-control" id="editTitlePost${model.getLastEditedPost().getId()}" name="title"
                               value="${model.getLastEditedPost().getTitle()}">
                    </div>
                </div>
                <div class="form-group">
                    <textarea id="editBodyPost${model.getLastEditedPost().getId()}" class="form-control" rows="10" name="body">${model.getLastEditedPost().getBody()}</textarea>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <button type="button" class="btn btn-danger" id="recordButton">
                            Record
                        </button>
                        <button type="button" class="btn btn-danger" id="stopButton">
                            Stop
                        </button>
                        <span class="interim" id="content_curr"></span>
                        <button type="submit" id="editSavePost${model.getLastEditedPost().getId()}" class="btn btn-primary pull-right">Save</button>
                    </div>
                </div>
            </form>
        </div>

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
                                var val = $('#edit-body-textarea').val();
                                $('#edit-body-textarea').val(val + ' ' + text);
                            }
                            $('#content_curr').html(text);

                        },
                        function(info) {
                            $('#save-button').css('backgroundColor','hsla(' + info.maxFreq + ',60%,60%,0.3)');
                        }
                );
            };

            stopButton.onclick = dict.stop;
        </script>
</div>
</@layout.layout>