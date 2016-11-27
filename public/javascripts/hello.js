$('.ui.form')
  .form({
    fields: {
      email : 'empty',
      password : ['minLength[6]', 'empty']
    }
  });

$('.field.description')
  .popup({
    popup : $('.description.popup'),
    on    : 'click'
  });