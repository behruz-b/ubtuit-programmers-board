$ ->
  my.initAjax()

  Glob = window.Glob || {}

  apiUrl =
    addUser: '/createUser'
    addLanguage: '/createLanguage'


  defaultUserdata =
    firstName: ''
    lastName: ''
    login: ''
    password: ''
    photo: ''

  defaultLanguageData =
    name: ''
    logo: ''

  defaultDirectionData =
    direction: ''

  vm = ko.mapping.fromJS
    user: defaultUserdata
    language: defaultLanguageData
    direction: defaultDirectionData
    enableSubmitButton: yes
    errorText: ''

  $contentFile = $('input[name=attachedFile]')
  $contentFile.change ->
    filePath = $(this).val()
    fileName = filePath.replace(/^.*[\\\/]/, '')

    reAllowedTypes = /^.+((\.jpg)|(\.jpeg)|(\.png))$/i
    if !reAllowedTypes.test(fileName)
      alert('Only PNG or JPG files are allowed.')
      return false

    $('#file-name').html fileName
    vm.enableSubmitButton yes

  formData = null
  $fileUploadForm = $('#file-upload-form')
  $fileUploadForm.fileupload
    dataType: 'text'
    autoUpload: no
    replaceFileInput: false
    multipart: true
    add: (e, data) ->
      formData = data
      vm.errorText('')
    fail: (e, data) ->
      handleError(data.jqXHR)
      vm.enableSubmitButton(yes)
    done: (e, data) ->
      result = data.result


  handleError = (error) ->
    if error.status is 500 or (error.status is 400 and error.responseText)
      toastr.error(error.responseText)
    else
      toastr.error('Something went wrong! Please try again.')

  vm.addLanguage = ->
    toastr.clear()
    if (!vm.language.name())
      toastr.error("Please enter a name")
      return no
    else if(!vm.language.logo())
      toastr.error("Please upload a logo the Language")
      return no
    else
      data = ko.mapping.toJS(vm.language())
      $.ajax
        url: apiUrl.addLanguage
        type: 'POST'
        data: JSON.stringify(data)
        dataType: 'json'
        contentType: 'application/json'
      .fail handleError
      .done (response) ->
        toastr.success(response)

  vm.addDirection = ->
    toastr.clear()
    if (!vm.direction.name())
      toastr.error("Please enter a Direction Name")
      return no
    else
      data = ko.mapping.toJS(vm.direction())
      $.ajax
        url: apiUrl.addDirection
        type: 'POST'
        data: JSON.stringify(data)
        dataType: 'json'
        contentType: 'application/json'
      .fail handleError
      .done (response) ->
        toastr.success(response)

  $fileUploadForm.submit ->
    toastr.clear()
    if (!vm.user.firstName())
      toastr.error("Please enter first name")
      return no
    else if (!vm.user.lastName())
      toastr.error("Please enter last name")
      return no
    else if (!vm.user.login())
      toastr.error("Please enter login")
      return no
    else if (!vm.user.password())
      toastr.error("Please enter password")
      return no
    else if (!vm.user.password())
      toastr.error("Please enter password")
      return no
    else
      if formData
        vm.enableSubmitButton(no)
        formData.submit()



  ko.applyBindings {vm}