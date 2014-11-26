/**
 * Created by dungvn3000 on 3/18/14.
 */
Ext.define('sunerp.view.nhanvien.HeSoLuongEdit', {
    extend: 'sunerp.view.core.BaseEditView',
    alias: 'widget.heSoLuongEdit',
    title: 'Edit HeSoLuong',
    width: 400,
    requires: [
        'sunerp.component.NhanVienCb',
        'sunerp.component.NhanVienPicker',
        'sunerp.component.MonthCb'
    ],
    controller: 'sunerp.controller.nhanvien.HeSoLuongEditCtr',
    initComponent: function () {
        this.items = [
            {
                xtype: 'form',
                border: false,
                items: [
                    {
                        xtype: 'nhanvienpicker',
                        name: 'maNv',
                        width: 360,
                        allowBlank: false,
                        fieldLabel: 'Mã NV'
                    },
                    {
                        xtype: 'numberfield',
                        name: 'heSoLuong',
                        minValue:0,
                        width: 360,
                        allowBlank: false,
                        fieldLabel: 'Hệ số lương'
                    },
                    {
                        xtype: 'monthcb',
                        name: 'month',
                        width: 360,
                        allowBlank: false,
                        fieldLabel: 'Tháng'
                    },
                    {
                        xtype: 'numberfield',
                        name: 'year',
                        width: 360,
                        allowBlank: false,
                        fieldLabel: 'Năm'
                    }
                ]
            }
        ];

        this.callParent(arguments);
    }
});