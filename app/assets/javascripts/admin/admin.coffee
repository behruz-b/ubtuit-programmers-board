$ ->
  my.initAjax()

  Glob = window.Glob || {}

  apiUrl =


  defaultUserdata =
    firstName: ''
    lastName: ''
    login: ''
    password: ''
    photo: ''


  vm = ko.mapping.fromJS
    user: defaultUserdata


  handleError = (error) ->
    if error.status is 500 or (error.status is 400 and error.responseText)
      toastr.error(error.responseText)
    else
      toastr.error('Something went wrong! Please try again.')



  ko.applyBindings {vm}