//这只是为了自动更新数据文本，如果您直接在页面上进行编辑，并不是实际效果所必需的
$('[data-text]').on('keyup', function(){
  $(this).attr('data-text', $(this).text());
});